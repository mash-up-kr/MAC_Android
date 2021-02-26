package mashup.data.sample

import mashup.data.sample.repository.SampleRepository

object SampleInjection {

    fun provideRepository(): SampleRepository =
        SampleRepository(SampleApiProvider.provideSampleApi())
}