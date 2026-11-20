#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
python3 - "$ROOT" <<'PY'
from pathlib import Path
import re
import sys
project = Path(sys.argv[1])
errors = []
for source_root in (project / 'src/main/java', project / 'src/test/java'):
    for path in source_root.rglob('*.java'):
        lines = path.read_text(encoding='utf-8').splitlines()
        for number, line in enumerate(lines, 1):
            if len(line) > 150:
                errors.append(f"{path}:{number}: line exceeds 150 characters")
            if line.rstrip() != line:
                errors.append(f"{path}:{number}: trailing whitespace")
            if 'TODO' in line or 'FIXME' in line:
                errors.append(f"{path}:{number}: unfinished marker")
        for index, line in enumerate(lines):
            if re.search(r'\b(public|private|protected)\s+.*\(', line) and '{' in line:
                depth = 0
                opened = False
                for end in range(index, len(lines)):
                    depth += lines[end].count('{') - lines[end].count('}')
                    opened = opened or '{' in lines[end]
                    if opened and depth == 0:
                        if end - index + 1 > 20:
                            errors.append(f"{path}:{index + 1}: method exceeds 20 lines")
                        break
if errors:
    print('\n'.join(errors))
    sys.exit(1)
print('Lint passed: line length, whitespace, unfinished markers, and method size.')
PY
