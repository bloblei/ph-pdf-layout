package com.helger.pdflayout4.element.list;

import java.io.IOException;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.pdflayout4.base.AbstractPLRenderableObject;
import com.helger.pdflayout4.base.IPLRenderableObject;
import com.helger.pdflayout4.element.table.PLTable;
import com.helger.pdflayout4.element.table.PLTableCell;
import com.helger.pdflayout4.render.PageRenderContext;
import com.helger.pdflayout4.render.PreparationContext;
import com.helger.pdflayout4.spec.SizeSpec;
import com.helger.pdflayout4.spec.WidthSpec;

/**
 * A simple bullet point list.
 * 
 * @author Philip Helger
 * @since 5.0.10
 */
public class PLBulletPointList extends AbstractPLRenderableObject <PLBulletPointList>
{
  private final PLTable m_aTable;
  private final IBulletPointCreator m_aBulletPointCreator;

  public PLBulletPointList (final float fWidthSpec, @Nonnull final IBulletPointCreator aBulletPointCreator)
  {
    ValueEnforcer.notNull (aBulletPointCreator, "BulletPointCreator");
    // Using different width types requires to NOT use a colspan
    m_aTable = new PLTable (WidthSpec.abs (fWidthSpec), WidthSpec.star ());
    m_aTable.setID ("bulletpoint-list");
    m_aBulletPointCreator = aBulletPointCreator;
  }

  @Nonnull
  public final PLTable getUnderlyingTable ()
  {
    return m_aTable;
  }

  @Nonnull
  public final IBulletPointCreator getBulletPointCreator ()
  {
    return m_aBulletPointCreator;
  }

  public void addBulletPoint (@Nonnull final IPLRenderableObject <?> aElement)
  {
    final int nBulletPointIndex = m_aTable.getRowCount ();

    final PLTableCell aCellLeft = new PLTableCell (m_aBulletPointCreator.getBulletPointElement (nBulletPointIndex));
    aCellLeft.setID ("bulletpoint");
    final PLTableCell aCellRight = new PLTableCell (aElement);
    aCellRight.setID ("content");

    m_aTable.addRow (aCellLeft, aCellRight);
  }

  @Override
  protected SizeSpec onPrepare (final PreparationContext aCtx)
  {
    return m_aTable.prepare (aCtx);
  }

  @Override
  protected void onMarkAsNotPrepared ()
  {
    m_aTable.internalMarkAsNotPrepared ();
  }

  @Override
  protected void onRender (final PageRenderContext aCtx) throws IOException
  {
    m_aTable.render (aCtx);
  }
}