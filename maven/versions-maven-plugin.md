
mvn versions:set -DnewVersion=1.2.0-SNAPSHOT

mvn versions:revert

mvn versions:update-child-modules
