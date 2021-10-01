package com.example.doglibraryapplication.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.doglibraryapplication.Dog
import com.example.doglibraryapplication.databinding.ActivityEditDogProfileBinding
import com.example.doglibraryapplication.model_domain.Breed
import com.google.android.material.snackbar.Snackbar
import java.util.*

class EditDogProfileActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityEditDogProfileBinding

    //о вьюшке сами должны создавать презентеры!
    //private lateinit var presenter: Contract.Presenter //todo добавить его
    private var presenter: Contract.Presenter = EditDogProfilePresenter()
    //класть презентер в сейвд инстент стейт - идея не очень
        //но если презентер пережил поворот, все равно надо заново заполнить экран, достать его из презентера
        //в он аттач можно делать, там вызывать нужные состояния, сет стейт, сет дог...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDogProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //он аттач сразу прописываем
        presenter.onAttach(this)

        //обычно тут дергаем презентер
        initView()
    }

    private fun initView() {
        binding.saveButton.setOnClickListener {
            presenter.onSave(gatherDog()) //gather - это собрать
        }
        binding.dogBreedEditExt.addTextChangedListener {
            //presenter.onChangeBreed(binding.dogBreedEditExt.toString())
            presenter.onChangeBreed(it.toString())
        }
    }

    //пройдет по всем сьюшкам и вернет нам собаку
    private fun gatherDog(): Dog {
        //UUID.randomUUID().toString() //так делают рандомные айдишники
        return Dog(
            binding.dogNameEditExt.text.toString(),
            binding.dogAgeEditExt.text.toString().toInt(),
            binding.dogWeightEditExt.text.toString().toInt(),
            Breed(binding.dogBreedEditExt.text.toString()),
            "",
        )
    }

    override fun setState(state: Contract.ViewState) {
        //если состяние = лоадинг, то визибл
        //лучше писать через вен, длинно и сложно
//        binding.progressView.isVisible = state == Contract.ViewState.LOADING
//        binding.contentLayout.isVisible = state != Contract.ViewState.LOADING && state != Contract.ViewState.ERROR

        //но так моргнет сначала
        binding.contentLayout.isVisible = false
        binding.progressView.isVisible = false

        when (state) {
            Contract.ViewState.LOADING -> {
                binding.progressView.isVisible = true
            }
            Contract.ViewState.IDLE -> {
                binding.contentLayout.isVisible = true
            }
            Contract.ViewState.ERROR -> {
                binding.contentLayout.isVisible = true
                Snackbar.make(binding.root, "No success!", Snackbar.LENGTH_SHORT).show()
            }
            Contract.ViewState.SUCCESS -> {
                 binding.successImageView.isVisible = true
            }
        }
    }

    override fun setBreedError(errorCode: Int) {
        binding.dogBreedEditExt.error = getErrorStringByCode(errorCode)
    }

    override fun saveDog(dog: Dog) {
        binding.dogNameEditExt.setText(dog.name)
        binding.dogWeightEditExt.setText(dog.weight.toString()) //если не стринг, то решит, что это ресурс, и свалится
        binding.dogAgeEditExt.setText(dog.age.toString())
        binding.dogBreedEditExt.setText(dog.breed.name)
    }

    private fun getErrorStringByCode(errorCode: Int): String {
        return "Породы не существует!" //todo //больше типов ошибок!
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}