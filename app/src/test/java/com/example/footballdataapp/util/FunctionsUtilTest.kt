package com.example.footballdataapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FunctionsUtilTest{

    @Test
    fun `empty inputDate returns custom string`(){
        val result = FunctionsUtil.validateDateInput("", "yyyy-MM-dd", "yyyy/MM/dd")
        assertThat(result).isEqualTo("...")
    }

    @Test
    fun `empty inputFormat returns custom string`(){
        val result = FunctionsUtil.validateDateInput("2023-10-03", "", "yyyy/MM/dd")
        assertThat(result).isEqualTo("...")
    }

    @Test
    fun `empty outputFormat returns empty string`(){
        val result = FunctionsUtil.validateDateInput("2023-10-03", "yyyy-MM-dd", "")
        assertThat(result).isEqualTo("")
    }

    @Test
    fun `invalid outputFormat returns wrong date`(){
        val result = FunctionsUtil.validateDateInput("2023-10-03", "yyyy-MM-dd", "ymd")
        assertThat(result).isNotEqualTo("2023/10/03")
    }

    @Test
    fun `inconsistent inputDate and inputFormat returns custom string`(){
        val result = FunctionsUtil.validateDateInput("2023/10/03", "yyyy-MM-dd", "yyyy/MM/dd")
        assertThat(result).isEqualTo("...")
    }

    @Test
    fun `consistent inputDate, inputFormat and valid outputFormat returns date`(){
        val result = FunctionsUtil.validateDateInput("2023-10-03", "yyyy-MM-dd", "yyyy/MM/dd")
        assertThat(result).isEqualTo("2023/10/03")
    }

}