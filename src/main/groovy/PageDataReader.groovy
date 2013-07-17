import groovy.transform.Immutable
@Immutable
class PageDataReader {
    List propertiesToRead
    Closure getPageStringClos

    /**
    * scriptからreaderが読むべきプロパティのリストと
    * 読んだプロパティからページのテキストを生成するクロージャを取得し
    * それに基づいてファイルからPageDataを生成するPageDataReaderを作成する
    */
    static PageDataReader createReader(File readerSetupScript) {
        if (readerSetupScript == null) {
            throw new IllegalArgumentException('argument is null')
        }
        def readerConfig = new ConfigSlurper().parse(readerSetupScript.toURL())
        List properties = readPropertyList readerConfig
        Closure clos = readGetPageStringClos readerConfig
        new PageDataReader(propertiesToRead:properties, getPageStringClos:clos)
        //new PageDataReader(['prop1','prop2'], {})
    }
    static List readPropertyList(ConfigObject config) {
        config.properties
    }
    static Closure readGetPageStringClos(ConfigObject config) {
        config.clos
    }
    /**
    * ページ作成に必要なプロパティをファイルから読み込み
    * PageDataを作成する
    */
    PageData readPropertiesAndCreatePageData(File src) {
        // propertiesToReadに指定されたpropertyを
        // srcから読み込んでpropertiesに格納する
        Map properties = [:].withDefault{""}
        def readerConfig = new ConfigSlurper().parse(src.toURL())
        propertiesToRead.each {
            properties[it] = readerConfig[it]
        }
        new PageData(properties, getPageStringClos)
    }
}

