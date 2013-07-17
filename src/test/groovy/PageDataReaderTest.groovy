import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

class PageDataReaderTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    @Test
    void ファイルからプロパティprop1とprop2及びクロージャclosを読み取るreaderをつくれる() {
        File configFile = folder.newFile('simpleReader.groovy')
        configFile.withPrintWriter { writer ->
            writer.println "properties = ['prop1', 'prop2']"
            writer.println "clos = { -> 'clos from configFile'}"
        }
        PageDataReader sut = PageDataReader.createReader(configFile)
        assertThat(sut.propertiesToRead, is(['prop1','prop2']))
        assertThat(sut.getPageStringClos.call(), is('clos from configFile'))
    }
    @Test
    void 設定ファイルからpropertiesを読み取れる() {
        File configFile = folder.newFile('simpleReader.groovy')
        configFile.withPrintWriter { writer ->
            writer.println "properties = ['prop1', 'prop2']"
        }
        def readerConfig = new ConfigSlurper().parse(configFile.toURL())
        def actual = PageDataReader.readPropertyList readerConfig
        assertThat actual, is(['prop1','prop2'])
    }
    @Test
    void 設定ファイルからclosを読み取れる() {
        File configFile = folder.newFile('simpleReader.groovy')
        configFile.withPrintWriter { writer ->
            writer.println "clos = { -> 'this is a test'}"
        }
        def readerConfig = new ConfigSlurper().parse(configFile.toURL())
        def actual = PageDataReader.readGetPageStringClos readerConfig
        assertThat actual.call(), is('this is a test')
    }
    @Test
    void resourceにおいた設定ファイルから読み取れる() {
        File configFile = 
          new File(Thread.currentThread().getContextClassLoader()
            .getResource('simpleReaderFromResources.groovy').toURI())
        PageDataReader sut = PageDataReader.createReader(configFile)
        assertThat(sut.propertiesToRead, is(['prop1','prop2']))
        assertThat(sut.getPageStringClos.call(), is('clos from configFile'))
    }
}

