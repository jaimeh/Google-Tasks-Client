Google Tasks Client:
===================

- The Google Tasks Client will be using OAuth, so you'll need to contact a PM for access to the API Key.
- GTC will be using the Google Tasks API to deal with the transfer of data. Go read up at: https://developers.google.com/google-apps/tasks/


------------------

Maven:
===================

If you don't have Maven installed (you'll need to install <a href="http://mxcl.github.com/homebrew/">brew</a> first):

    $ brew install maven

Navigate to location of google-tasks-client directory:

    $ mvn clean install


------------------

Installing the ActionBarSherlock dependencies manually:
==================
<ol>
  <li>Download <a href="https://github.com/JakeWharton/ActionBarSherlock/zipball/4.0.0">ActionBarSherlock</a></li>
  <li>Unzip</li>
  <li>In Eclipse: 'File > New > Create roject from existing source, Click Browse on location, choose library folder from extracted folder above, and click finish'</li>
  <li>Right click on ActionBarSherlock > Properties, click Android tab, tick Android 4.0.3 as a Project Build Target</li>
  <li>tick 'isLibrary' in The Library, Click Apply and OK.</li>
  <li>Right click on Reddit Google Tasks > Properties, click Android tab, click Add... > Choose ActionBarSherlock > Click OK > Click Apply and OK</li>
  <li>'Project > Clean...'</li>
</ol>