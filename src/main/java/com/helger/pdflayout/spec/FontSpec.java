/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.pdflayout.spec;

import java.awt.Color;
import java.io.IOException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Defines a text font specification containing the font, the font size and the
 * text color.
 *
 * @author Philip Helger
 */
@NotThreadSafe
@MustImplementEqualsAndHashcode
public class FontSpec
{
  /** The default font color: black */
  public static final Color DEFAULT_COLOR = Color.BLACK;

  private final PreloadFont m_aPreloadFont;
  private final float m_fFontSize;
  private final Color m_aColor;
  // Status vars
  private PDDocument m_aFontDoc;
  private LoadedFont m_aLoadedFont;

  public FontSpec (@Nonnull final PreloadFont aPreloadFont, @Nonnegative final float fFontSize)
  {
    this (aPreloadFont, fFontSize, DEFAULT_COLOR);
  }

  public FontSpec (@Nonnull final PreloadFont aPreloadFont,
                   @Nonnegative final float fFontSize,
                   @Nonnull final Color aColor)
  {
    this (aPreloadFont, fFontSize, aColor, null, null);
  }

  private FontSpec (@Nonnull final PreloadFont aPreloadFont,
                    @Nonnegative final float fFontSize,
                    @Nonnull final Color aColor,
                    @Nullable final PDDocument aFontDoc,
                    @Nullable final LoadedFont aLoadedFont)
  {
    ValueEnforcer.notNull (aPreloadFont, "Font");
    ValueEnforcer.isGT0 (fFontSize, "FontSize");
    ValueEnforcer.notNull (aColor, "Color");
    m_aPreloadFont = aPreloadFont;
    m_fFontSize = fFontSize;
    m_aColor = aColor;
    m_aFontDoc = aFontDoc;
    m_aLoadedFont = aLoadedFont;
  }

  /**
   * @return The font to use. Never <code>null</code>.
   */
  @Nonnull
  public PreloadFont getPreloadFont ()
  {
    return m_aPreloadFont;
  }

  /**
   * @return The font size in points. Always &gt; 0.
   */
  @Nonnegative
  public float getFontSize ()
  {
    return m_fFontSize;
  }

  /**
   * @return The text color to use.
   */
  @Nonnull
  public Color getColor ()
  {
    return m_aColor;
  }

  private void _loadFontForDocument (@Nonnull final PDDocument aDoc) throws IOException
  {
    m_aLoadedFont = new LoadedFont (m_aPreloadFont.loadPDFont (aDoc));
    m_aFontDoc = aDoc;
  }

  /**
   * @param aDoc
   *        The PDDocument for which the font should be loaded.
   * @return The loaded font or the cached value.
   * @throws IOException
   *         In case font loading fails
   */
  @Nonnull
  public LoadedFont getAsLoadedFont (@Nonnull final PDDocument aDoc) throws IOException
  {
    // Cache to avoid parsing TTF over and over again
    if (m_aLoadedFont == null)
      _loadFontForDocument (aDoc);
    else
      if (m_aFontDoc != aDoc)
      {
        // Load font again, if the PDDocument changed
        _loadFontForDocument (aDoc);
      }
    return m_aLoadedFont;
  }

  /**
   * Return a clone of this object but with a different font.
   *
   * @param aNewFont
   *        The new font to use. Must not be <code>null</code>.
   * @return this if the fonts are equal - a new object otherwise.
   */
  @Nonnull
  public FontSpec getCloneWithDifferentFont (@Nonnull final PreloadFont aNewFont)
  {
    ValueEnforcer.notNull (aNewFont, "NewFont");
    if (aNewFont.equals (m_aPreloadFont))
      return this;
    // Don't copy loaded font!
    return new FontSpec (aNewFont, m_fFontSize, m_aColor, null, null);
  }

  /**
   * Return a clone of this object but with a different font size.
   *
   * @param fNewFontSize
   *        The new font size to use. Must be &gt; 0.
   * @return this if the font sizes are equal - a new object otherwise.
   */
  @Nonnull
  public FontSpec getCloneWithDifferentFontSize (final float fNewFontSize)
  {
    ValueEnforcer.isGT0 (fNewFontSize, "FontSize");
    if (EqualsHelper.equals (fNewFontSize, m_fFontSize))
      return this;
    return new FontSpec (m_aPreloadFont, fNewFontSize, m_aColor, m_aFontDoc, m_aLoadedFont);
  }

  /**
   * Return a clone of this object but with a different color.
   *
   * @param aNewColor
   *        The new color to use. May not be <code>null</code>.
   * @return this if the colors are equal - a new object otherwise.
   */
  @Nonnull
  public FontSpec getCloneWithDifferentColor (@Nonnull final Color aNewColor)
  {
    ValueEnforcer.notNull (aNewColor, "NewColor");
    if (aNewColor.equals (m_aColor))
      return this;
    return new FontSpec (m_aPreloadFont, m_fFontSize, aNewColor, m_aFontDoc, m_aLoadedFont);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final FontSpec rhs = (FontSpec) o;
    return m_aPreloadFont.equals (rhs.m_aPreloadFont) &&
           EqualsHelper.equals (m_fFontSize, rhs.m_fFontSize) &&
           m_aColor.equals (rhs.m_aColor);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aPreloadFont).append (m_fFontSize).append (m_aColor).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("font", m_aPreloadFont)
                                       .append ("fontSize", m_fFontSize)
                                       .append ("color", m_aColor)
                                       .toString ();
  }
}
