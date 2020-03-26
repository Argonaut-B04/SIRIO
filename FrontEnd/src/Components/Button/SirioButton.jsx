import React from 'react';
import classes from './SirioButton.module.css';

/**
 * Komponen Button untuk Sirio secara Umum
 * 
 * Props yang tersedia:
 * - purple / blue
 * - recommended / hyperlink / text / disabled
 * - circular (optional)
 * - classes                : cssClass, kelas tambahan untuk SirioButton
 */
export default class SirioButton extends React.Component {

    render() {
        let color;
        if (this.props.purple) {
            color = classes.purple;
        } else if (this.props.blue) {
            color = classes.blue;
        }

        let style;
        if (this.props.recommended) {
            style = classes.recommended;
        } else if (this.props.hyperlink) {
            style = classes.hyperlink;
        } else if (this.props.text) {
            style = classes.text;
        } else if (this.props.disabled) {
            style = classes.disabled;
        }

        let borderRadius;
        if (this.props.circular) {
            borderRadius = classes.borderCircular;
        } else {
            borderRadius = classes.borderNormal;
        }

        let fullClass;
        if (color && style) {
            fullClass = [style, color].join(' ');
        } else if (color) {
            fullClass = color;
        } else if (style) {
            fullClass = style;
        }

        fullClass = [fullClass, borderRadius].join(' ');

        if (this.props.classes) {
            fullClass = [fullClass, this.props.classes].join(' ');
        }

        return (
            <button
                onClick={this.props.onClick}
                className={fullClass}
            >
                <h5 className={classes.buttonTitle}>
                    {this.props.children}
                </h5>
            </button>
        )
    }
}