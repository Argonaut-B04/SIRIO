import React, { Component } from 'react'
import classes from './SirioRectangularButton.module.css';

class SirioRectangularButton extends Component {
    render() {
        var fileName;
        var className;
        switch (this.props.type) {
            case "delete":
                fileName = "/delete_outline_24px.png";
                className = classes.delete;
                break;
            case "add":
                fileName = "/add_24px.png";
                className = classes.add;
                break;
            default:
                fileName = "/add_24px.png";
                className = classes.add;
                break;
        }
        return (
            <button onClick={this.props.onClick} className={[className, this.props.className].join(" ")} type="button">
                <img src={process.env.PUBLIC_URL + fileName} alt="sirio rectangular button" height="22px"/>
            </button>
        )
    }
}

export default SirioRectangularButton
