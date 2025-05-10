package com.bao.singaporeotcalculator.domain.utils

object SalaryCalculator {
    fun calculateHourlyRate(
        basicSalary: Double?,
        maxClaimWage: Double?,
        claimType: ClaimType,
        salaryType: SalaryType
    ): Double {
        if (basicSalary == null || basicSalary <= 0) {
            return 0.0
        }

        val dMaxClaimWage = maxClaimWage ?: Double.MAX_VALUE

        val hourlyRateLimit = when (claimType) {
            ClaimType.MONTHLY -> 12.0 * dMaxClaimWage / (52 * 44)
            ClaimType.HOURLY -> dMaxClaimWage
        }

        val hourlyRateNormal = when (salaryType) {
            SalaryType.MONTHLY -> 12.0 * basicSalary / (52 * 44)
            SalaryType.HOURLY -> basicSalary
        }

        val hourlyRate = minOf(hourlyRateLimit, hourlyRateNormal)

        return hourlyRate
    }

    fun calculateHourlyOverTimePay(
        hourlyRate : Double,
        rate : Double
    ): Double {
         return hourlyRate * rate
    }
}

enum class SalaryType {
    MONTHLY,
    HOURLY
}

enum class ClaimType {
    MONTHLY,
    HOURLY
}