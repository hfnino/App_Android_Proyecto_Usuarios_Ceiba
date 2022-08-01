package co.com.personal.hnino.pruebaceiba.domain

import co.com.personal.hnino.pruebaceiba.data.DataRepository
import javax.inject.Inject

class DeleteAllFromDataBaseUseCase @Inject constructor(private val repository: DataRepository) {

    suspend operator fun invoke(){
        repository.deleteAllFromDataBase()
    }
}