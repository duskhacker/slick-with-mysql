This is a quick example of using [Slick](http://slick.typesafe.com/ "Slick") with [MySQL](http://mysql.com "MySQL")

It doesn't do much other than collect the odd bits of knowledge needed for setting the correct dependencies
in the build file and the correct imports to get a working setup for running Slick with MySQL.

To run the specs:

	sbt test
	
You'll need a MySQL instance running on port 3306 (the default) for the specs to pass.