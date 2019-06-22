# presto-geoname

Presto plugin functions to returns the nearest geoname's centroid point (https://www.geonames.org/) from a given latitude and longitude.

Searching the nearest geoname's centroid point is done efficiently by using google s2 geometry (http://s2geometry.io/)   


### Packaging
`mvn install:install-file -Dfile=local-maven-repo/atlas-0.1.0.jar -DgroupId=atlas -DartifactId=atlas -Dversion=0.1.0 -Dpackaging=jar -DgeneratePom=true`

`mvn package`
