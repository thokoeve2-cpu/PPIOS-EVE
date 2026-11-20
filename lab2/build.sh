#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
cd "$ROOT"
rm -rf target/classes
mkdir -p target/classes
find src/main/java -name '*.java' -print > target/sources.list
javac --release 17 -Xlint:all -d target/classes @target/sources.list
jar --create --file target/book-warehouse-lab2-1.0.0.jar --main-class Main -C target/classes .
printf 'Build complete: %s\n' "$ROOT/target/book-warehouse-lab2-1.0.0.jar"
