import base64
import json
import os

ROOT = os.path.dirname(os.path.abspath(__file__))
REPO = os.path.dirname(ROOT)
RES = os.path.join(REPO, "common", "src", "main", "resources", "assets", "combat_oddities")
NAMESPACE = "combat_oddities"

STAGES = ["netherite_anvil", "chipped_netherite_anvil", "damaged_netherite_anvil"]
FACING_ROTATIONS = {"east": 270, "north": 180, "south": 0, "west": 90}
DIRECTIONS = ["north", "east", "south", "west", "up", "down"]


def num(value):
    value = round(float(value), 4)
    return int(value) if value == int(value) else value


def write_json(path, data):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as handle:
        json.dump(data, handle, indent=2)
        handle.write("\n")


def write_png(path, source):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "wb") as handle:
        handle.write(base64.b64decode(source.split(",", 1)[1]))


def convert_element(cube, u_scale, v_scale):
    inflate = cube.get("inflate") or 0
    element = {
        "name": cube.get("name", "cube"),
        "from": [num(v - inflate) for v in cube["from"]],
        "to": [num(v + inflate) for v in cube["to"]],
    }
    if cube.get("shade") is False:
        element["shade"] = False

    rotation = cube.get("rotation") or [0, 0, 0]
    axes = [i for i in range(3) if abs(rotation[i]) > 1e-6]
    if axes:
        axis = axes[0]
        element["rotation"] = {
            "angle": num(rotation[axis]),
            "axis": "xyz"[axis],
            "origin": [num(v) for v in cube.get("origin", [8, 8, 8])],
        }
        if cube.get("rescale"):
            element["rotation"]["rescale"] = True

    faces = {}
    for direction in DIRECTIONS:
        face = cube.get("faces", {}).get(direction)
        if not face or face.get("texture") is None:
            continue
        uv = face["uv"]
        entry = {
            "uv": [num(uv[0] * u_scale), num(uv[1] * v_scale), num(uv[2] * u_scale), num(uv[3] * v_scale)],
            "texture": "#anvil",
        }
        if face.get("rotation"):
            entry["rotation"] = face["rotation"]
        if face.get("cullface"):
            entry["cullface"] = face["cullface"]
        if face.get("tint", -1) >= 0:
            entry["tintindex"] = face["tint"]
        faces[direction] = entry
    element["faces"] = faces
    return element


def convert_anvil():
    with open(os.path.join(ROOT, "netherite_anvil.bbmodel"), "r", encoding="utf-8") as handle:
        data = json.load(handle)

    res = data.get("resolution", {"width": 16, "height": 16})
    u_scale = 16.0 / res["width"]
    v_scale = 16.0 / res["height"]

    elements = [convert_element(cube, u_scale, v_scale) for cube in data["elements"]
                if cube.get("type", "cube") == "cube" and cube.get("export", True)]

    template = {
        "parent": "minecraft:block/block",
        "render_type": "minecraft:cutout",
        "textures": {"particle": "#anvil"},
        "elements": elements,
    }
    if data.get("display"):
        template["display"] = data["display"]
    write_json(os.path.join(RES, "models", "block", "template_netherite_anvil.json"), template)

    for index, stage in enumerate(STAGES):
        write_png(os.path.join(RES, "textures", "block", stage + ".png"), data["textures"][index]["source"])
        write_json(os.path.join(RES, "models", "block", stage + ".json"), {
            "parent": NAMESPACE + ":block/template_netherite_anvil",
            "textures": {"anvil": NAMESPACE + ":block/" + stage},
        })
        write_json(os.path.join(RES, "models", "item", stage + ".json"), {
            "parent": NAMESPACE + ":block/" + stage,
        })
        variants = {}
        for facing, y in FACING_ROTATIONS.items():
            variant = {"model": NAMESPACE + ":block/" + stage}
            if y:
                variant["y"] = y
            variants["facing=" + facing] = variant
        write_json(os.path.join(RES, "blockstates", stage + ".json"), {"variants": variants})

    names = [t["name"] for t in data["textures"]]
    print("anvil: %d elements, textures %s -> %s" % (len(elements), names, STAGES))


def convert_villager():
    with open(os.path.join(ROOT, "Bladesmith.bbmodel"), "r", encoding="utf-8") as handle:
        data = json.load(handle)
    source = data["textures"][0]["source"]
    for entity in ("villager", "zombie_villager"):
        write_png(os.path.join(RES, "textures", "entity", entity, "profession", "master_swordsmith.png"), source)
    print("villager: profession texture from %s" % data["textures"][0]["name"])


convert_anvil()
convert_villager()
