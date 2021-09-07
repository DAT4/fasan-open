package sh.mama.fasanapp

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import sh.mama.fasanapp.util.PreferenceKeys.USER
import sh.mama.fasanapp.models.User
import sh.mama.fasanapp.mvvm.UserViewModel
import sh.mama.fasanapp.ui.Navigator
import sh.mama.fasanapp.ui.theme.FasanAppTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import sh.mama.fasanapp.api.Resource
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FasanAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigator(vm = viewModel)
                }
            }
        }
    }
}

private val Context.dataStore by preferencesDataStore("user")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
    private val ds = appContext.dataStore
    suspend fun putData(mode: User) {
        ds.edit { data ->
            data[USER] = setOf(mode.username, mode.password)
        }
    }

    val data: Flow<Resource<User>> = ds.data.map { data ->
        data[USER]?.let {
            Resource.Success(User(it.first(), it.last()))
        } ?: Resource.Error("Nope")
    }
}

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}

@HiltAndroidApp
class DoorApp : Application()
