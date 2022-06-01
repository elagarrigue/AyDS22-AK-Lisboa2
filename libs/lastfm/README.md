# LastFM - Lisboa 2

## Setup    

#### 1. Agregar el siguiente submodulo de git
```git
git submodule add https://github.com/brancomartinezc/LastFM-Lisboa2.git libs/lastfm
```
#### 2. Agregar las siguientes lineas al archivo settings.gradle
```kotlin
include ':LastFM'
project(':LastFM').projectDir = new File('libs/lastfm')
```
#### 3. Sincronizar el proyecto en los archivos de gradle. 
#### 4. Agregar la siguiente linea en las dependencias de build.gradle
```kotlin
implementation project(":LastFM")
```
#### 5. Sincronizar el proyecto en los archivos de gradle.


## Uso

#### 1. Importar el modulo
```kotlin
import ayds.lisboa2.lastFM.LastFMService

```

#### 2. Obtener una instancia del servicio
```kotlin 
val lastFMService: LastFMService = LastFMInjector.lastFMService
```

#### 3. Obtener informacion del servicio
```kotlin
lastFMService.getArtist(name)
```

## Edge cases responses

En caso de que no se encuentre informacion del artista se encuentra un artista se lanza una excepcion

En caso que no haya conexion a internet se retorna null