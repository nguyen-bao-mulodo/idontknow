Requirements
Basically, you need to do 3 things:

Change the structure of your Android Project.
The building tools (Maven, Android Maven Plugin, Android SDK).
Configure a 'travis.yml' file to make it work on Travis CI.
#Step 1: Application structure
You have to change the directory structure of your Android application to comply with the structure shown below.

 Project-parent-folder:
   -Android-Application-folder
      -pom.xml
   -Android-Tests-folder
      -pom.xml
   -Android-library-project-folder
      -pom.xml
   -pom.xml
NOTES:

We assume you are using the Eclipse IDE.
If you already have the project in your Eclipse Workspace, you will need to delete it from your workspace and re-import it again (after you change the structure).
#Step 2: MAVEN
Download Maven and extract the distribution archive to the directory in which you want to install Maven (e.g. apache-maven-3.0.5-bin.tar.gz).

Supossing you extracted the archive on your $HOME directory, then run on your terminal:

export M2_HOME=$HOME/apache-maven-3.0.5
export M2=$M2_HOME/bin
export PATH=$PATH:$M2
source ~/.bashrc
To verify that it is correctly installed:

mvn --version
#Step 3: Eclipse Maven Android Plugin
You have to install a plugin to make the Android project work with Maven within Eclipse.

In Eclipse, go to:

"Help" > "Eclipse Marketplace"
If you are using Eclipse ADT or do not have the Eclipse Marketplace, install it from:

"Help" > "Install new Software" > "Switch to the Juno Repository" >
"General Purpose Tools" > "Marketplace Client"
Once in Eclipse MarketPlace:

Search for: android m2e
Install Android Configurator for M2E
#Step 4: Eclipse Maven Android Project
To make Eclipse detect the project dependencies, you have to convert the Android and dependent Android Library projects to Maven:

Right click on each project:
"Maven" > "Convert to Maven project"
Right click on your Android project,
"Maven" > "Update Project"
Select the related projects and accept the changes.
This should get rid of any Eclipse error about some dependencies not found.

Also, you have to configure the 'pom.xml' files for each project. You can adapt these samples according to your project:

POM file for the Project parent folder
POM file for the Application folder
POM file for the Tests project folder
POM file for an Android library project
#Step 5: Install Maven Android SDK Deployer
If you want to avoid the pain of configuring the Android SDKs in your local Maven repositories, this tool is a must.

Once you run this on your terminal, you will have all the Android SDK available as Maven dependencies.

git clone https://github.com/mosabua/maven-android-sdk-deployer.git
cd android-sdk-deployer
mvn install
#Step 6: Install External Android Library Projects
If you are using any Android library project, you have to perform some additional steps:

Compress the "library" folder of each library project and change the extension of the file to '.apklib'
The .apklib files should be put in your Application 'libs' folder (e.g. AndroidProject/libs)
Install each library on your local maven repository, as follows:
Run on your terminal, from your Android Application Project's root folder:

mvn install:install-file \
-Dfile=libs/your-library-file-1.apklib \
-DgroupId=com.your-library-package \
-DartifactId=your-library-artifact-id \
-Dversion=1 \
-Dpackaging=apklib
#Step 7: Installing and deploying the application
These commands are for building the application and deploying to a device.

mvn clean install
mvn android:deploy
#Step 8: Running Tests
On the Emulator:

mvn clean install -Pintegration-tests -Dandroid.device=your_emulator_name
on the Device:

mvn clean install -Pintegration-tests -Dandroid.device=usb
Step 9: Travis CI configuration
If everything works as it should in your local environment, then, all you need is to add a '.travis.yml' file to your application.

This is an example of the configuration we are using:

language: java
jdk: oraclejdk7
env:
 matrix:
   android-16 is always included
   - ANDROID_SDKS=sysimg-16           ANDROID_TARGET=android-16  ANDROID_ABI=armeabi-v7a
   - ANDROID_SDKS=android-17,sysimg-17 ANDROID_TARGET=android-17  ANDROID_ABI=armeabi-v7a
before_install:
   Install base Android SDK
  - sudo apt-get update -qq
  - if [ `uname -m` = x86_64 ]; then sudo apt-get install -qq --force-yes libgd2-xpm ia32-libs ia32-libs-multiarch > /dev/null; fi
  - wget http://dl.google.com/android/android-sdk_r21.1-linux.tgz
  - tar xzf android-sdk_r21.1-linux.tgz
  - export ANDROID_HOME=$PWD/android-sdk-linux
  - export PATH=${PATH}:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools

   Install required components.
   For a full list, run `android list sdk -a --extended`
   Note that sysimg-16 downloads the ARM, x86 and MIPS images (we should optimize this).
   Other relevant API's:
   addon-google_apis-google-16
  - android update sdk --filter platform-tools,android-16,extra-android-support,66,$ANDROID_SDKS --no-ui --force > /dev/null

   Create and start emulator
  - echo no | android create avd --force -n test -t $ANDROID_TARGET --abi $ANDROID_ABI
  - emulator -avd test -no-skin -no-audio -no-window &

before_script:
  - chmod +x wait_for_emulator
  - ./wait_for_emulator

script: mvn install -Pintegration-tests -Dandroid.device=test
Also, note that we use the 'wait-for-emulator' script:

!/bin/bash

bootanim=""
failcounter=0
until [[ "$bootanim" =~ "stopped" ]]; do
   bootanim=`adb -e shell getprop init.svc.bootanim 2>&1`
   echo "$bootanim"
   if [[ "$bootanim" =~ "not found" ]]; then
      let "failcounter += 1"
      if [[ $failcounter -gt 3 ]]; then
        echo "Failed to start emulator"
        exit 1
      fi
   fi
   sleep 1
done
echo "Done"
Both the Travis configuration file and Wait-for-emulator script were adapted from this Maven Android Example.

Finally
Push some changes to your Github repository (you need to have the Travis hook activated) and you should see that Travis builds and tests your Android application. Nice!

References
Android Maven Plugin Docs
Kistner's Android builds blogpost
Check out MagmaConf, our very own international conference for Ruby specialists, coming soon in Mexico!