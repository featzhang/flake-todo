package com.github.featzhang.flake.client.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * The control used to specify the properties encoding of the ResourceBundle.
 */
public class EncodableResourceControl extends ResourceBundle.Control {

  private final Charset charset;

  public EncodableResourceControl(Charset charset) {
    this.charset = charset;
  }

  /**
   * Instantiates a resource bundle for the given bundle name of the
   * given format and locale, using the given class loader if
   * necessary. This method returns <code>null</code> if there is no
   * resource bundle available for the given parameters. If a resource
   * bundle can't be instantiated due to an unexpected error, the
   * error must be reported by throwing an <code>Error</code> or
   * <code>Exception</code> rather than simply returning
   * <code>null</code>.
   *
   * <p>If the <code>reload</code> flag is <code>true</code>, it
   * indicates that this method is being called because the previously
   * loaded resource bundle has expired.
   * </p>
   * <p>The default implementation instantiates a
   * <code>ResourceBundle</code> as follows.
   * </p>
   * <ul>
   *
   * <li>The bundle name is obtained by calling {@link
   * #toBundleName(String, java.util.Locale) toBundleName(baseName,
   * locale)}.</li>
   *
   * <li>If <code>format</code> is <code>"java.class"</code>, the
   * {@link Class} specified by the bundle name is loaded by calling
   * {@link ClassLoader#loadClass(String)}. Then, a
   * <code>ResourceBundle</code> is instantiated by calling {@link
   * Class#newInstance()}.  Note that the <code>reload</code> flag is
   * ignored for loading class-based resource bundles in this default
   * implementation.</li>
   *
   * <li>If <code>format</code> is <code>"java.properties"</code>,
   * {@link #toResourceName(String, String) toResourceName(bundlename,
   * "properties")} is called to get the resource name.
   * If <code>reload</code> is <code>true</code>, {@link
   * ClassLoader#getResource(String) load.getResource} is called
   * to get a {@link java.net.URL} for creating a {@link
   * java.net.URLConnection}. This <code>URLConnection</code> is used to
   * {@linkplain java.net.URLConnection#setUseCaches(boolean) disable the
   * caches} of the underlying resource loading layers,
   * and to {@linkplain java.net.URLConnection#getInputStream() get an
   * <code>InputStream</code>}.
   * Otherwise, {@link ClassLoader#getResourceAsStream(String)
   * loader.getResourceAsStream} is called to get an {@link
   * java.io.InputStream}. Then, a {@link
   * java.util.PropertyResourceBundle} is constructed with the
   * <code>InputStream</code>.</li>
   *
   * <li>If <code>format</code> is neither <code>"java.class"</code>
   * nor <code>"java.properties"</code>, an
   * <code>IllegalArgumentException</code> is thrown.</li>
   *
   * </ul>
   *
   * @param baseName the base bundle name of the resource bundle, a fully
   *                 qualified class name
   * @param locale   the locale for which the resource bundle should be
   *                 instantiated
   * @param format   the resource bundle format to be loaded
   * @param loader   the <code>ClassLoader</code> to use to load the bundle
   * @param reload   the flag to indicate bundle reloading; <code>true</code>
   *                 if reloading an expired resource bundle,
   *                 <code>false</code> otherwise
   * @return the resource bundle instance, or <code>null</code> if none could be found.
   * @throws NullPointerException        if <code>bundleName</code>, <code>locale</code>,
   *                                     <code>format</code>, or <code>loader</code> is
   *                                     <code>null</code>, or if <code>null</code> is returned by
   *                                     {@link #toBundleName(String, java.util.Locale) toBundleName}
   * @throws IllegalArgumentException    if <code>format</code> is unknown, or if the resource
   *                                     found for the given parameters contains malformed data.
   * @throws ClassCastException          if the loaded class cannot be cast to <code>ResourceBundle</code>
   * @throws IllegalAccessException      if the class or its nullary constructor is not
   *                                     accessible.
   * @throws InstantiationException      if the instantiation of a class fails for some other
   *                                     reason.
   * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
   * @throws SecurityException           If a security manager is present and creation of new
   *                                     instances is denied. See {@link Class#newInstance()}
   *                                     for details.
   * @throws java.io.IOException                 if an error occurred when reading resources using
   *                                     any I/O operations
   */
  public ResourceBundle newBundle(
      String baseName,
      Locale locale,
      String format,
      ClassLoader loader,
      boolean reload
  ) throws IllegalAccessException, InstantiationException, IOException {
    // The below is a copy of the default implementation.
    String bundleName = toBundleName(baseName, locale);
    String resourceName = toResourceName(bundleName, "properties");
    ResourceBundle bundle = null;
    InputStream stream = null;
    if (reload) {
      URL url = loader.getResource(resourceName);
      if (url != null) {
        URLConnection connection = url.openConnection();
        if (connection != null) {
          connection.setUseCaches(false);
          stream = connection.getInputStream();
        }
      }
    } else {
      stream = loader.getResourceAsStream(resourceName);
    }
    if (stream != null) {
      try {
        // Only this line is changed to make it to read properties files as UTF-8.
        bundle = new PropertyResourceBundle(new InputStreamReader(stream, charset));
      } finally {
        stream.close();
      }
    }
    return bundle;
  }
}