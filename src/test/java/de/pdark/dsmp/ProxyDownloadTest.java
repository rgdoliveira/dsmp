/*
 * Copyright 2002-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.pdark.dsmp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URL;


public class ProxyDownloadTest
{
    private File cacheDir;
    private Config config;

    @Test
    public void testMkdirs () throws Exception
    {
        URL url = new URL ("http://repo1.maven.org/maven2/org/apache/commons/commons-parent/1/commons-parent-1.pom");
        File f = RequestHandler.getCacheFile(url, cacheDir);
        String expected = cacheDir.getAbsolutePath().replace(File.separatorChar, '/')+"/repo1.maven.org/maven2/org/apache/commons/commons-parent/1/commons-parent-1.pom";
        String s = f.getAbsolutePath().replace(File.separatorChar, '/');
        assertEquals(expected, s);
    }

    @Test
    @Ignore
    public void testDownload () throws Exception
    {
        URL url = new URL ("http://repo1.maven.org/maven2/org/apache/commons/commons-parent/1/commons-parent-1.pom");
        File f = File.createTempFile("zz", "commons-parent-1.pom");
        ProxyDownload d = new ProxyDownload (url, f, config);
        d.download();
        
        assertEquals (7616, f.length());
    }
    
    @Before
    public void setUp () throws Exception
    {
        cacheDir = new File(org.apache.commons.io.FileUtils.getTempDirectory(), System.currentTimeMillis() + "");
        cacheDir.mkdirs();
        config = new Config("src/test/resources");
        System.setProperty("dsmp.conf", "dsmp-test.conf.xml");
        config.reload ();

    }
    @After
    public void tearDown () throws Exception
    {
        System.clearProperty("dsmp.conf");
    }

}
