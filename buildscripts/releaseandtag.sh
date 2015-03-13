#!/bin/sh
git pull --rebase
cp jevents/build/libs/*.jar release/
git add release/
git commit -m "Release version" || echo "Nothing to commit"
git tag -a v0.1.$SNAP_PIPELINE_COUNTER -m "release v0.1.$SNAP_PIPELINE_COUNTER"
git push origin master
git push origin --tags
