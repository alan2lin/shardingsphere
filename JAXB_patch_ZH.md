
# 更新升级中用来更改 import 的脚本
set -x
repodir=/data/gitrepo/shardingsphere/
# search for all  file extensions
find ${repodir} -type f  -name "*.*" -exec bash -c 'ext="${1##*.}"; [ ${#ext} -lt 5 ] && echo "$ext "' _ {} \; | sort -u > suffix.txt

# find import javax.xml.bind.annotation.*
find ${repodir} -type f  \( -name "*.java" -o -name "*.md" -o -name "*.xml" -o -name "*.yaml" -o -name "*.yml" -o -name "*.txt" -o -name "*.toml" \) -exec bash -c 'sed -n  "s/import javax\(\.xml\.bind\.annotation\.[^;]\+;\)/import jakarta\1/p" "$1" | awk -v file="$1" -v OFS=, "{if (\$0 != \"\") print file, NR, \$0}"' _ {} \; > preview.txt


# find import javax.xml.bind.JAXB*
find ${repodir} -type f  \( -name "*.java" -o -name "*.md" -o -name "*.xml" -o -name "*.yaml" -o -name "*.yml" -o -name "*.txt" -o -name "*.toml" \) -exec bash -c 'sed -n  "s/import javax\(\.xml\.bind\.JAXB[^;]\+;\)/import jakarta\1/p" "$1" | awk -v file="$1" -v OFS=, "{if (\$0 != \"\") print file, NR, \$0}"' _ {} \; >> preview.txt

# replace import javax.xml.bind.annotation.*  with import jakarta.xml.bind.annotation.*
find ${repodir} -type f  \( -name "*.java" -o -name "*.md" -o -name "*.xml" -o -name "*.yaml" -o -name "*.yml" -o -name "*.txt" -o -name "*.toml" \) -exec bash -c 'sed -i  "s/import javax\(\.xml\.bind\.annotation\.[^;]\+;\)/import jakarta\1/g" "$1" ' _ {} \;

# replace import javax.xml.bind.JAXB*  with import jakarta.xml.bind.JAXB*
find ${repodir} -type f  \( -name "*.java" -o -name "*.md" -o -name "*.xml" -o -name "*.yaml" -o -name "*.yml" -o -name "*.txt" -o -name "*.toml" \) -exec bash -c 'sed -i  "s/import javax\(\.xml\.bind\.JAXB[^;]\+;\)/import jakarta\1/g" "$1" ' _ {} \;

cd shardingsphere
mvn -T 1C clean install -Prelease


# <!-- https://mvnrepository.com/artifact/jakarta.activation/jakarta.activation-api -->
# <dependency>
#     <groupId>jakarta.activation</groupId>
#     <artifactId>jakarta.activation-api</artifactId>
#     <version>2.1.2</version>
# </dependency>
# 
# <!-- https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime -->
# <dependency>
#     <groupId>org.glassfish.jaxb</groupId>
#     <artifactId>jaxb-runtime</artifactId>
#     <version>4.0.4</version>
# </dependency>
# 
# <!-- https://mvnrepository.com/artifact/jakarta.xml.bind/jakarta.xml.bind-api -->
# <dependency>
#     <groupId>jakarta.xml.bind</groupId>
#     <artifactId>jakarta.xml.bind-api</artifactId>
#     <version>4.0.1</version>
# </dependency>
