# Popular Movies (Final Stage) 
Android App that display details of Most Popular and Top Rated Movies.

Also displays Movies while using offline.

Created as a part of [Udacity Android Developer Nanodegree](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801) 

## API used
The Movie Data Base (TMDb)

__NOTE:__ Add your API key to ``` Buil.gradle ``` file as specified below in order to run the app.
```
 buildTypes.each{
        it.buildConfigField 'String', 'MOBDB_API_KEY',"\"write_your_key_here\""
    }
}

```

You can obtain a key from [themoviedb.org](https://www.themoviedb.org)
## Libraries Used
[Picasso](http://square.github.io/picasso/)

[Retrofit](http://square.github.io/retrofit/)

[Simple SQL Provider](https://github.com/ckurtm/simple-sql-provider)

[Wasabeef RecyclerView Animator](https://github.com/wasabeef/recyclerview-animators) 

[balysv material-ripple](https://github.com/balysv/material-ripple)

## Concepts Learned
- Building a fully featured app that looks and feel natural on the latest Android operating System.
- Optimizing the UI experience for both phones and tablets.

## General Project Guideline
App conforms to common standards found in the [Android Nanodegree General Project Guidelines](http://udacity.github.io/android-nanodegree-guidelines/core.html)

## License

```
Copyright 2016 Mudit Srivastava

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```





