/*******************************************************************************
 * Copyright (c) 2009 Dmitry Grushin <dgrushin@gmail.com>.
 * 
 * This file is part of GridMe.
 * 
 * GridMe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GridMe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with GridMe.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Dmitry Grushin <dgrushin@gmail.com> - initial API and implementation
 ******************************************************************************/
package statemachine.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import statemachine.StatemachineFactory;
import statemachine.StatemachinePackage;
import statemachine.Transition;

/**
 * This is the item provider adapter for a {@link statemachine.Transition} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TransitionItemProvider
  extends NamedItemProvider
  implements	
    IEditingDomainItemProvider,	
    IStructuredItemContentProvider,	
    ITreeItemContentProvider,	
    IItemLabelProvider,	
    IItemPropertySource		
{
  /**
   * This constructs an instance from a factory and a notifier.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TransitionItemProvider(AdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  /**
   * This returns the property descriptors for the adapted class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
  {
    if (itemPropertyDescriptors == null)
    {
      super.getPropertyDescriptors(object);

      addFromPropertyDescriptor(object);
      addToPropertyDescriptor(object);
      addPreserveTimersPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the From feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addFromPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_Transition_from_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_Transition_from_feature", "_UI_Transition_type"),
         StatemachinePackage.Literals.TRANSITION__FROM,
         true,
         false,
         true,
         null,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the To feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addToPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_Transition_to_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_Transition_to_feature", "_UI_Transition_type"),
         StatemachinePackage.Literals.TRANSITION__TO,
         true,
         false,
         true,
         null,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Preserve Timers feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addPreserveTimersPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_Transition_preserveTimers_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_Transition_preserveTimers_feature", "_UI_Transition_type"),
         StatemachinePackage.Literals.TRANSITION__PRESERVE_TIMERS,
         true,
         false,
         false,
         ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
   * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
  {
    if (childrenFeatures == null)
    {
      super.getChildrenFeatures(object);
      childrenFeatures.add(StatemachinePackage.Literals.TRANSITION__GUARD);
      childrenFeatures.add(StatemachinePackage.Literals.TRANSITION__DELAY);
      childrenFeatures.add(StatemachinePackage.Literals.TRANSITION__SIGNALS);
    }
    return childrenFeatures;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EStructuralFeature getChildFeature(Object object, Object child)
  {
    // Check the type of the specified child object and return the proper feature to use for
    // adding (see {@link AddCommand}) it as a child.

    return super.getChildFeature(object, child);
  }

  /**
   * This returns Transition.gif.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object getImage(Object object)
  {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/Transition"));
  }

  /**
   * This returns the label text for the adapted class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getText(Object object)
  {
    String label = ((Transition)object).getName();
    return label == null || label.length() == 0 ?
      getString("_UI_Transition_type") :
      getString("_UI_Transition_type") + " " + label;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(Transition.class))
    {
      case StatemachinePackage.TRANSITION__PRESERVE_TIMERS:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case StatemachinePackage.TRANSITION__GUARD:
      case StatemachinePackage.TRANSITION__DELAY:
      case StatemachinePackage.TRANSITION__SIGNALS:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
        return;
    }
    super.notifyChanged(notification);
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
   * that can be created under this object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
    super.collectNewChildDescriptors(newChildDescriptors, object);

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__GUARD,
         StatemachineFactory.eINSTANCE.createGetParameter()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__GUARD,
         StatemachineFactory.eINSTANCE.createCall()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__GUARD,
         StatemachineFactory.eINSTANCE.createStringValue()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__GUARD,
         StatemachineFactory.eINSTANCE.createBooleanValue()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__GUARD,
         StatemachineFactory.eINSTANCE.createLongValue()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__DELAY,
         StatemachineFactory.eINSTANCE.createGetParameter()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__DELAY,
         StatemachineFactory.eINSTANCE.createCall()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__DELAY,
         StatemachineFactory.eINSTANCE.createStringValue()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__DELAY,
         StatemachineFactory.eINSTANCE.createBooleanValue()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__DELAY,
         StatemachineFactory.eINSTANCE.createLongValue()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__SIGNALS,
         StatemachineFactory.eINSTANCE.createGetParameter()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__SIGNALS,
         StatemachineFactory.eINSTANCE.createCall()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__SIGNALS,
         StatemachineFactory.eINSTANCE.createStringValue()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__SIGNALS,
         StatemachineFactory.eINSTANCE.createBooleanValue()));

    newChildDescriptors.add
      (createChildParameter
        (StatemachinePackage.Literals.TRANSITION__SIGNALS,
         StatemachineFactory.eINSTANCE.createLongValue()));
  }

  /**
   * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection)
  {
    Object childFeature = feature;
    Object childObject = child;

    boolean qualify =
      childFeature == StatemachinePackage.Literals.TRANSITION__GUARD ||
      childFeature == StatemachinePackage.Literals.TRANSITION__DELAY ||
      childFeature == StatemachinePackage.Literals.TRANSITION__SIGNALS;

    if (qualify)
    {
      return getString
        ("_UI_CreateChild_text2",
         new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
    }
    return super.getCreateChildText(owner, feature, child, selection);
  }

  /**
   * Return the resource locator for this item provider's resources.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ResourceLocator getResourceLocator()
  {
    return StatemachineEditPlugin.INSTANCE;
  }

}
