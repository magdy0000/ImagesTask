package com.beyond.imagetask

import com.beyond.imagetask.domian.models.ImagesModel
import com.beyond.imagetask.domian.repository.IRepository
import com.beyond.imagetask.domian.usecases.getimages.ImagesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException

class ImagesUseCaseTest {

    @Mock
    private lateinit var mockRepository: IRepository

    private lateinit var imagesUseCase: ImagesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        imagesUseCase = ImagesUseCase(mockRepository)
    }

    @Test
    fun `getImages should return success`() = runBlocking {
        val limit = 5
        val hasBreeds = true
        val mockImages = listOf(ImagesModel("1", "", "a", "", ""))

        `when`(mockRepository.getImages(limit, hasBreeds)).thenReturn(mockImages)

        val result = imagesUseCase.getImages(limit, hasBreeds)

        Assert.assertTrue(result.isSuccess())
        Assert.assertEquals(mockImages, result.getSuccessData())
    }

    @Test
    fun `getImages should return error`() = runBlocking {
        val limit = 5
        val hasBreeds = true
        val mockException = MockitoException("Mock error")

        `when`(mockRepository.getImages(limit, hasBreeds)).thenThrow(mockException)

        val result = imagesUseCase.getImages(limit, hasBreeds)

        Assert.assertTrue(!result.isSuccess())
        Assert.assertEquals(mockException.localizedMessage, result.getErrorMessage())
    }
}
