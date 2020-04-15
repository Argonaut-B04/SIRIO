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
        const { purple, blue, red, hover, recommended, hyperlink, hyperlinkLeft, text, disabled, circular, onClick, type, children } = this.props;

        // Prioritas warna: purple -> blue -> red
        let color;
        if (purple) {
            color = classes.purple;
        } else if (blue) {
            color = classes.blue;
        } else if (red) {
            color = classes.red;
        }

        // Hover atau non Hover
        let hoverStyle = hover && classes.hover;

        // Prioritas style: recommended => hyperlink => hyperlinkLeft => text => disabled
        let style;
        if (recommended) {
            style = classes.recommended;
        } else if (hyperlink) {
            style = classes.hyperlink;
        } else if (hyperlinkLeft) {
            style = classes.hyperlinkLeft;
        } else if (text) {
            style = classes.text;
        } else if (disabled) {
            style = classes.disabled;
        }

        // Border radius circular atau normal
        let borderRadius = circular && classes.borderCircular;

        var fullClass = [classes.sirioButton, color, hoverStyle, style, borderRadius, this.props.classes].join(' ');
        if (this.props.square) {
            fullClass = [classes.sirioButton, color, hoverStyle, classes.square].join(" ");
        }
        return (
            <button
                onClick={onClick}
                className={fullClass}
                type={type}
            >
                <h6 className={classes.buttonTitle}>
                    {children}
                </h6>
            </button>
        )
    }
}