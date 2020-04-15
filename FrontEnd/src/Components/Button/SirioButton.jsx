import React from 'react';
import classes from './SirioButton.module.css';

/**
 * Komponen Button untuk Sirio secara Umum
 * 
 * Props yang tersedia:
 * - purple / blue / red
 * - hover
 * - recommended / hyperlink / hyperlinkLeft / text / disabled
 * - circular (optional)
 * - classes                : cssClass, kelas tambahan untuk SirioButton
 * - onClick                : function, fungsi yang akan dijalankan ketika button di klik
 * - type                   : String, tipe button
 */
export default class SirioButton extends React.Component {

    render() {
        // Prioritas warna: purple -> blue -> red
        let color;
        if (this.props.purple) {
            color = classes.purple;
        } else if (this.props.blue) {
            color = classes.blue;
        } else if (this.props.red) {
            color = classes.red;
        }

        // Hover atau non Hover
        let hover = this.props.hover && classes.hover;

        // Prioritas style: recommended => hyperlink => hyperlinkLeft => text => disabled
        let style;
        if (this.props.recommended) {
            style = classes.recommended;
        } else if (this.props.hyperlink) {
            style = classes.hyperlink;
        } else if (this.props.hyperlinkLeft) {
            style = classes.hyperlinkLeft;
        } else if (this.props.text) {
            style = classes.text;
        } else if (this.props.disabled) {
            style = classes.disabled;
        }

        // Border radius circular atau normal
        let borderRadius = this.props.circular && classes.borderCircular;

        let fullClass = [classes.sirioButton, color, hover, style, borderRadius, this.props.classes].join(' ');
        return (
            <button
                onClick={this.props.onClick}
                className={fullClass}
                type={this.props.type}
            >
                <h6 className={classes.buttonTitle}>
                    {this.props.children}
                </h6>
            </button>
        )
    }
}