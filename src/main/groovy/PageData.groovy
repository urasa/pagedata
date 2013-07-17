class PageData {
    /**
    * このページ作成に必要な要素を格納する
    */
    Map properties
    /**
    * 与えられたプロパティからページのテキストを生成する
    */
    Closure getPageStringClos
    PageData(Map properties, Closure getPageStringClos) {
        this.properties = properties
        this.getPageStringClos = getPageStringClos
    }
    /**
    * このPageDataが格納している要素からページのテキストを生成する
    */
    String getPageString() {
        getPageStringClos(properties)
    }
}

