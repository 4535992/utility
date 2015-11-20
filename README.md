##############################################################
### A MODULE MAVEN WITH MANY UTILITY CLASS FOR MANY PURPOSE###
##############################################################
#########################
###Last Update: 2015-11-12
#########################
Class utility for the third library:
- Many Useful Class for Play with the JDK version 7
- JDOM
- JDOM2
- Hibernate 4 (in progress)
- Jena 2
- Sesame OpenRDF
- VirtJena (Sesame Adapter for Virtuoso server)
- JSoup
- JOOQ 3
- HTTP Apache
- ecc...

Other useful utility on these repository:
https://github.com/mhgrove/cp-common-utils
https://github.com/mhgrove/cp-openrdf-utils

(For the future try to put some best performance for JDK version 8)

Any Help,suggestion or improvement for this utility is welcome!!

P.S. I'm need help for create very useful method for update xml file without create/delete already exists file
with only jdk use wihout third library.

For the rest do what you want with these file.

[![Release](https://img.shields.io/github/release/p4535992/utility.svg?label=maven)](https://jitpack.io/p4535992/utility)

You can the dependency to this github repository With jitpack (https://jitpack.io/):

<!-- Put the Maven coordinates in your HTML: -->
 <pre class="prettyprint">&lt;dependency&gt;
  &lt;groupId&gt;com.github.p4535992&lt;/groupId&gt;
  &lt;artifactId&gt;utility&lt;/artifactId&gt;
  &lt;version&gt;<span id="latest_release">1.4.4</span>&lt;/version&gt;
&lt;/dependency&gt;  </pre>

<!-- Add this script to update "latest_release" span to latest version -->
<script>
      var user = 'p4535992'; // Replace with your user/repo
      var repo = 'utility'

      var xmlhttp = new XMLHttpRequest();
      xmlhttp.onreadystatechange = function() {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
              var myArr = JSON.parse(xmlhttp.responseText);
              populateRelease(myArr);
          }
      }
      xmlhttp.open("GET", "https://api.github.com/repos/" user + "/" + repo + "/releases", true);
      xmlhttp.send();

      function populateRelease(arr) {
          var release = arr[0].tag_name;
          document.getElementById("latest_release").innerHTML = release;
      }
</script>
