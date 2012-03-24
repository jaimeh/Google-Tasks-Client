Google Tasks Client:
===================

- The Google Tasks Client will be using OAuth, so you'll need to contact a PM for access to the API Key.
- GTC will be using the Google Tasks API to deal with the transfer of data. Go read up at: https://developers.google.com/google-apps/tasks/
- <strike>As we haven't yet integrated Maven (in the 'Features' ticket tracker), you'll need to install the dependencies manually.</strike>

------------------

Maven:
===================

If you don't have Maven installed (you'll need to install <a href="http://mxcl.github.com/homebrew/">brew</a> first):

    $ brew install maven

Navigate to location of google-tasks-client directory:

    $ mvn clean install


------------------

Installing the dependencies manually:
==================
<ol>
  <li>Download <a href="http://google-api-java-client.googlecode.com/files/google-api-java-client-1.7.0-beta.zip">google-api-java-client</a></li>
  <li>Download <a href="http://mavenrepo.google-api-java-client.googlecode.com/hg/com/google/apis/google-api-services-tasks/v1-rev2-1.4.0-beta/google-api-services-tasks-v1-rev2-1.4.0-beta.zip">google-api-services-tasks</a></li>
  <li>Unzip</li>
  <li>In Eclipse: 'Right-click on the project > Build path > Add External Archives...'</li>
  <li>Add all of the .jar files found in the directories and their 'dependencies' subdirectories</li>
  <li>'Project > Clean...'</li>
</ol>
