import React from 'react';
import classes from './SirioButton.module.css';
import ReactTooltip from 'react-tooltip';

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

    componentDidMount() {
        if (this.props.tooltip) {
            ReactTooltip.rebuild()
        }
    }

    componentWillUnmount() {
        ReactTooltip.hide()
    }

    render() {
        const { purple, blue, red, hover, recommended, hyperlink, hyperlinkLeft, text, disabled, circular, onClick, title, type, children, tooltip } = this.props;

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
            if (tooltip) {
                style = classes.textWithTooltip;
            } else {
                style = classes.text;
            }
        } else if (disabled) {
            if (tooltip) {
                style = classes.disableWithTooltip;
            } else {
                style = classes.disabled;
            }
        }

        // Border radius circular atau normal
        let borderRadius = circular && classes.borderCircular;

        var fullClass = [classes.sirioButton, color, hoverStyle, style, borderRadius, this.props.classes].join(' ');
        if (this.props.square) {
            fullClass = [classes.sirioButton, color, hoverStyle, style, classes.square, this.props.classes].join(" ");
        }

        return (
            <button
                onClick={onClick}
                className={fullClass}
                type={type}
                title={title}
                data-tip={tooltip}
            >
                <h6 className={[classes.buttonTitle, this.props.insideClass].join(" ")}>
                    {children}
                </h6>
            </button>
        )
    }
}