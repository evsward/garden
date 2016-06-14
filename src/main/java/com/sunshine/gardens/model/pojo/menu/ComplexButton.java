package com.sunshine.gardens.model.pojo.menu;

/**
 * complex type button
 * @author LiuWenbin
 * @2015年9月5日
 */
public class ComplexButton extends Button {
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
