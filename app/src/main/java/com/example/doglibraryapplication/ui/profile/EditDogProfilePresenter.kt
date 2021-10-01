package com.example.doglibraryapplication.ui.profile

import android.os.Handler
import android.os.Looper
import com.example.doglibraryapplication.Dog
import com.example.doglibraryapplication.ui.profile.Contract

//сначала для прикидка просто имплементируем и переопределеяем функции
//потом подсоединяем вью

private  const val  MOCK_LOADING_DURATION = 3000L

class EditDogProfilePresenter : Contract.Presenter{
private var view: Contract.View? = null

    override fun onAttach(view: Contract.View) {
        this.view = view
        // сразу зададим на сдарте состояние
        view.setState(Contract.ViewState.IDLE)
    }

    override fun onDetach() {
        view = null
        //не надо нам тянуть дальше ссылку на вью! в нашем случае на активити
    }

    override fun onSave(dog: Dog) {
        //имитируем тут какую-то работу
        view?.setState(Contract.ViewState.LOADING)
        Handler(Looper.getMainLooper()).postDelayed({
        view?.setState(Contract.ViewState.ERROR)
        }, MOCK_LOADING_DURATION) //3000 тоже надо в константу
    }

    override fun onChangeBreed(breed: String) {
        //тут может быть логика на проверку породы
        view?.setBreedError(0)
    }
    //добавить проверку возраста и веса! напр, эта валидация при кнопке сохранения. лучше отдельный метод
    //для каждого поля
    //валидацию ввода еще хорошо, что где числа, там числа
}