package org.lion.utils.tools.jar2maven.pojo;

public  class MavenLocation {
        private String groupId;
        private String artifactId;
        private String version;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            String format = "        <dependency>\n" +
                    "            <groupId>%s</groupId>\n" +
                    "            <artifactId>%s</artifactId>\n" +
                    "            <version>%s</version>\n" +
                    "        </dependency>";

            return String.format(format, groupId, artifactId, version);
        }
    }