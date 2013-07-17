import groovy.transform.Immutable
@Immutable
class PageDataReader {
    List propertiesToRead
    Closure getPageStringClos

    /**
    * ページ作成に必要なプロパティをファイルから読み込み
    * PageDataを作成する
    */
    PageData readPropertiesAndCreatePageData(File src) {
        // propertiesToReadに指定されたpropertyを
        // srcから読み込んでpropertiesに格納する
        Map properties
        new PageData(properties, getPageStringClos)
    }

    /**
    * scriptからreaderが読むべきプロパティのリストと
    * 読んだプロパティからページのテキストを生成するクロージャを取得し
    * それに基づいてファイルからPageDataを生成するPageDataReaderを作成する
    */
    static PageDataReader createReader(File readerSetupScript) {
        List props
        Closure clos
        new PageDataReader(propertiesToRead:props, getPageStringClos:clos)
    }
}

