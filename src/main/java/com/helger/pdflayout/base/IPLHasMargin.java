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
package com.helger.pdflayout.base;

import javax.annotation.Nonnull;

import com.helger.pdflayout.spec.MarginSpec;

/**
 * Base interface for objects having a margin
 *
 * @author Philip Helger
 * @param <IMPLTYPE>
 *        Implementation type
 */
public interface IPLHasMargin <IMPLTYPE extends IPLHasMargin <IMPLTYPE>> extends IPLObject <IMPLTYPE>
{
  MarginSpec DEFAULT_MARGIN = MarginSpec.MARGIN0;

  /**
   * Set all margin values (left, top, right, bottom) to the same value. This
   * method may not be called after an element got prepared!
   *
   * @param fMargin
   *        The value to use.
   * @return this
   */
  @Nonnull
  default IMPLTYPE setMargin (final float fMargin)
  {
    return setMargin (fMargin, fMargin);
  }

  /**
   * Set all margin values. This method may not be called after an element got
   * prepared!
   *
   * @param fMarginY
   *        The Y-value to use (for top and bottom).
   * @param fMarginX
   *        The X-value to use (for left and right).
   * @return this
   */
  @Nonnull
  default IMPLTYPE setMargin (final float fMarginY, final float fMarginX)
  {
    return setMargin (fMarginY, fMarginX, fMarginY, fMarginX);
  }

  /**
   * Set all margin values to potentially different values. This method may not
   * be called after an element got prepared!
   *
   * @param fMarginTop
   *        Top
   * @param fMarginRight
   *        Right
   * @param fMarginBottom
   *        Bottom
   * @param fMarginLeft
   *        Left
   * @return this
   */
  @Nonnull
  default IMPLTYPE setMargin (final float fMarginTop,
                              final float fMarginRight,
                              final float fMarginBottom,
                              final float fMarginLeft)
  {
    return setMargin (new MarginSpec (fMarginTop, fMarginRight, fMarginBottom, fMarginLeft));
  }

  /**
   * Set the margin values. This method may not be called after an element got
   * prepared!
   *
   * @param aMargin
   *        Margin to use. May not be <code>null</code>.
   * @return this
   */
  @Nonnull
  IMPLTYPE setMargin (@Nonnull MarginSpec aMargin);

  /**
   * Set the top margin value. This method may not be called after an element
   * got prepared!
   *
   * @param fMargin
   *        The value to use.
   * @return this
   */
  @Nonnull
  default IMPLTYPE setMarginTop (final float fMargin)
  {
    return setMargin (getMargin ().getCloneWithTop (fMargin));
  }

  /**
   * Set the right margin value. This method may not be called after an element
   * got prepared!
   *
   * @param fMargin
   *        The value to use.
   * @return this
   */
  @Nonnull
  default IMPLTYPE setMarginRight (final float fMargin)
  {
    return setMargin (getMargin ().getCloneWithRight (fMargin));
  }

  /**
   * Set the bottom margin value. This method may not be called after an element
   * got prepared!
   *
   * @param fMargin
   *        The value to use.
   * @return this
   */
  @Nonnull
  default IMPLTYPE setMarginBottom (final float fMargin)
  {
    return setMargin (getMargin ().getCloneWithBottom (fMargin));
  }

  /**
   * Set the left margin value. This method may not be called after an element
   * got prepared!
   *
   * @param fMargin
   *        The value to use.
   * @return this
   */
  @Nonnull
  default IMPLTYPE setMarginLeft (final float fMargin)
  {
    return setMargin (getMargin ().getCloneWithLeft (fMargin));
  }

  /**
   * @return The current margin. Never <code>null</code>.
   */
  @Nonnull
  MarginSpec getMargin ();

  /**
   * @return The current top margin.
   */
  default float getMarginTop ()
  {
    return getMargin ().getTop ();
  }

  /**
   * @return The current right margin.
   */
  default float getMarginRight ()
  {
    return getMargin ().getRight ();
  }

  /**
   * @return The current bottom margin.
   */
  default float getMarginBottom ()
  {
    return getMargin ().getBottom ();
  }

  /**
   * @return The current left margin.
   */
  default float getMarginLeft ()
  {
    return getMargin ().getLeft ();
  }

  /**
   * @return The sum of left and right margin.
   */
  default float getMarginXSum ()
  {
    return getMargin ().getXSum ();
  }

  /**
   * @return The sum of top and bottom margin.
   */
  default float getMarginYSum ()
  {
    return getMargin ().getYSum ();
  }
}