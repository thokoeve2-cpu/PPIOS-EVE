#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
cd "$ROOT"
./build.sh
exec java -jar target/book-warehouse-lab2-1.0.0.jar
