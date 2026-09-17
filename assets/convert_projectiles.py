import base64
import json
import math
import os

ROOT = os.path.dirname(os.path.abspath(__file__))
REPO = os.path.dirname(ROOT)
RES = os.path.join(REPO, "common", "src", "main", "resources", "assets", "combat_oddities")
TEX_DIR = os.path.join(RES, "textures", "entity", "projectile")
JAVA_OUT = os.path.join(REPO, "common", "src", "main", "java", "com", "nyfaria",
                        "combat_oddities", "client", "model", "ProjectileModels.java")

MODELS = [
    ("AcidPiece", "acid_piece", "acidPiece"),
    ("ChargedBullet", "charged_bullet", "chargedBullet"),
    ("CorrosiveBolt", "corrosive_bolt", "corrosiveBolt"),
    ("Shard", "shard", "shard"),
    ("ZappingBeam", "zapping_beam", "zappingBeam"),
]

DIRECTIONS = ["north", "east", "south", "west", "up", "down"]


def f(value):
    return "%sF" % repr(round(float(value), 4))


def rad(degrees):
    return "%sF" % repr(round(math.radians(float(degrees)), 5))


def collect(data):
    cubes = {}
    for element in data.get("elements", []):
        if element.get("type", "cube") == "cube":
            cubes[element["uuid"]] = element
    groups = {g["uuid"]: g for g in data.get("groups", [])}
    return cubes, groups


def emit_part(node, groups, cubes, parent_origin, lines, parent_var, counter):
    group = groups[node["uuid"]]
    origin = group.get("origin", [0, 0, 0])
    rotation = group.get("rotation") or [0, 0, 0]
    name = group["name"]
    var = "part_%d" % counter[0]
    counter[0] += 1

    own_cubes = []
    child_groups = []
    for child in node.get("children", []):
        if isinstance(child, dict):
            child_groups.append(child)
        elif child in cubes:
            own_cubes.append(cubes[child])

    builder = ["CubeListBuilder.create()"]
    rotated = []
    for cube in own_cubes:
        cube_rot = cube.get("rotation") or [0, 0, 0]
        if any(abs(a) > 1e-6 for a in cube_rot):
            rotated.append(cube)
            continue
        builder.append(cube_line(cube, origin))

    offset = [origin[0] - parent_origin[0],
              parent_origin[1] - origin[1],
              origin[2] - parent_origin[2]]

    if any(abs(a) > 1e-6 for a in rotation):
        pose = "PartPose.offsetAndRotation(%s, %s, %s, %s, %s, %s)" % (
            f(offset[0]), f(offset[1]), f(offset[2]),
            rad(-rotation[0]), rad(rotation[1]), rad(-rotation[2]))
    else:
        pose = "PartPose.offset(%s, %s, %s)" % (f(offset[0]), f(offset[1]), f(offset[2]))

    lines.append("        PartDefinition %s = %s.addOrReplaceChild(\"%s\", %s, %s);"
                 % (var, parent_var, name, "\n                ".join(builder), pose))

    for index, cube in enumerate(rotated):
        emit_rotated_cube(cube, origin, var, index + 1, lines)

    for child in child_groups:
        emit_part(child, groups, cubes, origin, lines, var, counter)


def cube_line(cube, origin):
    fr, to = cube["from"], cube["to"]
    uv = cube.get("uv_offset", [0, 0])
    size = [to[i] - fr[i] for i in range(3)]
    inflate = cube.get("inflate", 0)
    box = "addBox(%s, %s, %s, %s, %s, %s" % (
        f(fr[0] - origin[0]), f(origin[1] - to[1]), f(fr[2] - origin[2]),
        f(size[0]), f(size[1]), f(size[2]))
    if inflate:
        box += ", new CubeDeformation(%s)" % f(inflate)
    box += ")"
    return ".texOffs(%d, %d).%s" % (int(uv[0]), int(uv[1]), box)


def emit_rotated_cube(cube, group_origin, parent_var, index, lines):
    fr, to = cube["from"], cube["to"]
    pivot = cube.get("origin", [0, 0, 0])
    rot = cube.get("rotation") or [0, 0, 0]
    uv = cube.get("uv_offset", [0, 0])
    size = [to[i] - fr[i] for i in range(3)]
    inflate = cube.get("inflate", 0)

    box = "addBox(%s, %s, %s, %s, %s, %s" % (
        f(fr[0] - pivot[0]), f(pivot[1] - to[1]), f(fr[2] - pivot[2]),
        f(size[0]), f(size[1]), f(size[2]))
    if inflate:
        box += ", new CubeDeformation(%s)" % f(inflate)
    box += ")"

    pose = "PartPose.offsetAndRotation(%s, %s, %s, %s, %s, %s)" % (
        f(pivot[0] - group_origin[0]), f(group_origin[1] - pivot[1]), f(pivot[2] - group_origin[2]),
        rad(-rot[0]), rad(rot[1]), rad(-rot[2]))

    lines.append("        %s.addOrReplaceChild(\"cube_r%d\", CubeListBuilder.create()\n"
                 "                .texOffs(%d, %d).%s, %s);"
                 % (parent_var, index, int(uv[0]), int(uv[1]), box, pose))


def build_method(src_name, method):
    with open(os.path.join(ROOT, src_name + ".bbmodel"), "r", encoding="utf-8") as handle:
        data = json.load(handle)

    cubes, groups = collect(data)
    res = data.get("resolution", {"width": 16, "height": 16})

    lines = ["    public static LayerDefinition %s() {" % method,
             "        MeshDefinition mesh = new MeshDefinition();",
             "        PartDefinition root = mesh.getRoot();"]

    lo = [min(c["from"][i] for c in cubes.values()) for i in range(3)]
    hi = [max(c["to"][i] for c in cubes.values()) for i in range(3)]
    center = [(lo[i] + hi[i]) / 2.0 for i in range(3)]

    counter = [0]
    loose = []
    for node in data.get("outliner", []):
        if isinstance(node, dict):
            emit_part(node, groups, cubes, center, lines, "root", counter)
        elif node in cubes:
            loose.append(cubes[node])

    if loose:
        builder = ["CubeListBuilder.create()"]
        for cube in loose:
            builder.append(cube_line(cube, center))
        lines.append("        root.addOrReplaceChild(\"loose\", %s, PartPose.ZERO);"
                     % "\n                ".join(builder))

    lines.append("        return LayerDefinition.create(mesh, %d, %d);"
                 % (res["width"], res["height"]))
    lines.append("    }")

    source = data["textures"][0]["source"].split(",", 1)[1]
    os.makedirs(TEX_DIR, exist_ok=True)
    with open(os.path.join(TEX_DIR, src_name_to_texture(src_name) + ".png"), "wb") as handle:
        handle.write(base64.b64decode(source))

    rotated_count = sum(1 for c in cubes.values() if any(abs(a) > 1e-6 for a in (c.get("rotation") or [0, 0, 0])))
    print("%-16s %d cubes (%d rotated preserved), %dx%d texture"
          % (src_name, len(cubes), rotated_count, res["width"], res["height"]))
    return lines


TEXTURE_NAMES = {name: tex for name, tex, _ in MODELS}


def src_name_to_texture(src_name):
    return TEXTURE_NAMES[src_name]


out = [
    "package com.nyfaria.combat_oddities.client.model;",
    "",
    "import net.minecraft.client.model.geom.PartPose;",
    "import net.minecraft.client.model.geom.builders.CubeDeformation;",
    "import net.minecraft.client.model.geom.builders.CubeListBuilder;",
    "import net.minecraft.client.model.geom.builders.LayerDefinition;",
    "import net.minecraft.client.model.geom.builders.MeshDefinition;",
    "import net.minecraft.client.model.geom.builders.PartDefinition;",
    "",
    "public final class ProjectileModels {",
    "",
    "    private ProjectileModels() {",
    "    }",
    "",
]

for src, tex, method in MODELS:
    out.extend(build_method(src, method))
    out.append("")

out.append("}")

os.makedirs(os.path.dirname(JAVA_OUT), exist_ok=True)
with open(JAVA_OUT, "w", encoding="utf-8") as handle:
    handle.write("\n".join(out))

print("wrote", os.path.relpath(JAVA_OUT, REPO))
