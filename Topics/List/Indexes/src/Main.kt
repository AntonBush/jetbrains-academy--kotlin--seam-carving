fun solution(products: List<String>, product: String) {
    products.forEachIndexed { i, s -> if (product == s) print("$i ") }
}