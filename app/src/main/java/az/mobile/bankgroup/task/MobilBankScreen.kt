package az.mobile.bankgroup.task

enum class MobilBankScreen(val route: String) {
    FEED("feed"),
    SYMBOL_DETAILS("symbol/{symbol}"),

    ;

    companion object {
        const val SYMBOL_ARG = "symbol"
        const val SYMBOL_DETAILS_DEEP_LINK = "stocks://symbol/{symbol}"

        fun symbolDetailsRoute(symbol: String): String = "symbol/$symbol"
    }
}
