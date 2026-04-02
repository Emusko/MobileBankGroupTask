package az.mobile.bankgroup.task.data.stock

import az.mobile.bankgroup.task.domain.model.Stock

object StockCatalog {

    val symbolOrder: List<String> = listOf(
        "AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "NVDA", "META", "NFLX", "AMD", "INTC",
        "ORCL", "CRM", "CSCO", "IBM", "JPM", "BAC", "WFC", "GS", "DIS", "PYPL",
        "ADBE", "AVGO", "QCOM", "TXN", "COST",
    )

    fun createInitialStocks(): List<Stock> {
        val t = System.currentTimeMillis()
        return listOf(
            Stock("AAPL", "Apple Inc.", "Consumer electronics, software, and digital services.", 178.50, t),
            Stock("MSFT", "Microsoft Corp.", "Cloud, productivity software, and enterprise platforms.", 415.20, t),
            Stock("GOOGL", "Alphabet Inc.", "Search, advertising, and cloud infrastructure.", 142.80, t),
            Stock("AMZN", "Amazon.com Inc.", "E-commerce, logistics, and AWS cloud computing.", 185.40, t),
            Stock("TSLA", "Tesla Inc.", "Electric vehicles, energy storage, and autonomy software.", 248.90, t),
            Stock("NVDA", "NVIDIA Corp.", "GPUs, AI accelerators, and data-center platforms.", 892.10, t),
            Stock("META", "Meta Platforms Inc.", "Social networks, messaging, and metaverse investments.", 505.30, t),
            Stock("NFLX", "Netflix Inc.", "Subscription streaming and original content production.", 485.60, t),
            Stock("AMD", "Advanced Micro Devices", "CPUs, GPUs, and semiconductors for PCs and servers.", 168.40, t),
            Stock("INTC", "Intel Corp.", "CPUs, foundry services, and edge computing products.", 43.25, t),
            Stock("ORCL", "Oracle Corp.", "Enterprise databases, cloud ERP, and infrastructure.", 125.70, t),
            Stock("CRM", "Salesforce Inc.", "CRM SaaS, analytics, and customer data platforms.", 268.15, t),
            Stock("CSCO", "Cisco Systems", "Networking hardware, security, and collaboration tools.", 48.90, t),
            Stock("IBM", "IBM Corp.", "Hybrid cloud, consulting, and mainframe software.", 172.35, t),
            Stock("JPM", "JPMorgan Chase", "Global banking, markets, and asset management.", 198.80, t),
            Stock("BAC", "Bank of America", "Retail banking, wealth management, and trading.", 35.42, t),
            Stock("WFC", "Wells Fargo", "Consumer and commercial banking across the U.S.", 58.17, t),
            Stock("GS", "Goldman Sachs", "Investment banking, trading, and asset management.", 445.60, t),
            Stock("DIS", "Walt Disney Co.", "Media networks, parks, and streaming (Disney+).", 112.25, t),
            Stock("PYPL", "PayPal Holdings", "Digital wallets, checkout, and merchant services.", 62.88, t),
            Stock("ADBE", "Adobe Inc.", "Creative software, document cloud, and marketing tech.", 558.40, t),
            Stock("AVGO", "Broadcom Inc.", "Semiconductors, infrastructure software, and networking.", 1342.50, t),
            Stock("QCOM", "Qualcomm Inc.", "Mobile chipsets, licensing, and IoT connectivity.", 162.30, t),
            Stock("TXN", "Texas Instruments", "Analog and embedded processors for industrial use.", 175.95, t),
            Stock("COST", "Costco Wholesale", "Membership retail warehouses and private-label goods.", 785.20, t),
        ).also { stocks ->
            require(stocks.size == symbolOrder.size) { "Catalog size mismatch" }
            require(stocks.map { it.symbol } == symbolOrder) { "Catalog order mismatch" }
        }
    }
}
