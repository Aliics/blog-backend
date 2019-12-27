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
for Gradle.
> ./gradlew shadowJar

Now you can just execute the created jar!
> java -jar build/libs/blog-backend-1.1.0-SNAPSHOT-all.jar

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
