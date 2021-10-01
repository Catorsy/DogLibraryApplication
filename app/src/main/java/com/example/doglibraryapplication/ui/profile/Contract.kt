package com.example.doglibraryapplication.ui.profile

import com.example.doglibraryapplication.Dog

class Contract {
    enum class ViewState {
        IDLE, LOADING, SUCCESS, ERROR
    }

    interface View {
        fun setState(state: ViewState)
        fun setBreedError(errorCode: Int) //если нет такой породы. Стрингом плохо.
        fun saveDog (dog: Dog)
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onDetach() //к нам присоединяется - отсоединяется вьюшка
        fun onSave(dog: Dog)
        fun onChangeBreed(breed: String) //реакция сервера: например, проверить доступность пород
    }
}