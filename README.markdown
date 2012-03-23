RAD Google Tasker:
==================

- Tasker will be using OAuth as a way to validate and sign Google accounts, so you'll need access to the key from one of the leads. Just ask!
- Tasker will be using the Google Tasks API to deal with the transfer of data. Go read up at: https://developers.google.com/google-apps/tasks/
- As we haven't yet integrated Maven (in the 'Features' ticket tracker), we'll just go ahead and install a few dependencies by hand:

------------------

Installing google-api-java-client and its dependencies.
==================
<ol>
  <li>Download the latest google-api-java-client from: http://code.google.com/p/google-api-java-client/</li>
  <li>Unzip it!</li>
  <li>In your Tasker fork 'Right-click on the project > Build path > Add External Archivesâ...'</li>
  <li>Add all of the .jar files found the google-api-java-client directory and its 'dependencies' subdirectory</li>
  <li>'Project > Clean...'</li>
</ol>