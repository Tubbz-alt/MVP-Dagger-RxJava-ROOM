package mvp.cooking.com.foodlist.data.repository

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mvp.cooking.com.foodlist.data.db.AppDatabase
import mvp.cooking.com.foodlist.data.model.Food
import mvp.cooking.com.foodlist.data.model.FoodDto
import mvp.cooking.com.foodlist.data.network.ApiDisposable
import mvp.cooking.com.foodlist.data.network.ApiError
import mvp.cooking.com.foodlist.data.network.ApiService
import io.reactivex.Observable

class AppRepoImp(
   val apiService: ApiService,
   val database: AppDatabase
) : AppRepository {
    private val TAG = AppRepoImp::class.java.simpleName
    override fun getFoods(success: (FoodDto) -> Unit, failure: (ApiError) -> Unit, terminate: () -> Unit): Disposable {
        return apiService
            .getHome()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<FoodDto>(
                    {

                        success(it)
                    },
                    failure
                )
            )
    }

    override fun insertFood(food: Food) : Disposable=
        Observable
            .fromCallable { database.foodDao().insertFood(food) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "food added: subscribe: $it")
            }


}