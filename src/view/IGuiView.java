package view;

import controller.Features;

/**
 * This interface represents all the operations to be offered by the IGuiView.
 * These operations are supposed to be a barebones set upon which other operations
 * may be developed.
 */
public interface IGuiView {
  /**
   * adds features to the existing gui for CMV.
   *
   * @param features is the individual features.
   */
  void addFeatures(Features features);



}
