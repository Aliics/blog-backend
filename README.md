# blog-backend

Simple Java **(11)** web service for querying the database for my blog, I
figured it would be nice to have a blog. So I figured I'd code one.

# Get a copy!

Clone the repo to your current directory.
> git clone https://github.com/Aliics/blog-backend.git

Jump inside the cloned repo.
> cd blog-backend

# How to run

For building the jar with all dependencies I decided on the `shadowJar` plugin
for Gradle. _(both the Docker route and the local route require this step)_
> ./gradlew shadowJar

##### Use Docker

Like most sane developers in 2020 I decided to **Dockerize** this entire
process. This way, just about no matter what you are running you should be able
to get this off the ground.

> docker build . -t blog-backend:1.1.0-SNAPSHOT

And now you have a fancy image made with a nice version tag, it's just as simple
to run this image in a Docker container.

> docker run -p 8080:8080 blog-backend:1.1.0-SNAPSHOT

I am fairly certain this should work on all operating systems.

##### Alternatively

Sometimes it is quicker to not have to run docker build every time, so you can
just run the jar on your machine.

Run the compiled jar on your machine. _This might result in some conflicts, for
example JRE version._
> java -jar build/libs/blog-backend.jar

# How to test

All tests are written in Java using Junit, so just use gradle.

Run the tests. Includes optional `--rerun-tasks` so you don't have to recompile
to run again.
> ./gradlew test --rerun-tasks

# Why Java?

When writing a backend I had three language options: _Rust_, _Kotlin_, and 
_Java_. My reasons are not permanent or even the perfect decision, but I
certainly enjoy all of these languages and would be proficient enough in each
to develop something similar. I chose Java because of simplicity and because
out of these languages, Java is the one I'm most experienced in.
