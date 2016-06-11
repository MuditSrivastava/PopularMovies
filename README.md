# Popular Movies Stage 1
Android App to show Most Popular and Top Rated Movies.

Also displays Movies while using offline.

Created as a part of Udacity Android Developer Nanodegree 

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

[Wasabeef RecyclerView Animator](https://github.com/wasabeef/recyclerview-animators)

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





