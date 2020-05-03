import React, { Component } from 'react'
import classes from './SirioRectangularButton.module.css';

export default class SirioRectangularButton extends Component {
    render() {
        const { type, onClick, className } = this.props;

        var fileName;
        var primaryClassName;
        switch (type) {
            case "delete":
                fileName = "/delete_outline_24px.png";
                primaryClassName = classes.delete;
                break;
            case "add":
                fileName = "/add_24px.png";
                primaryClassName = classes.add;
                break;
            default:
                fileName = "/add_24px.png";
                primaryClassName = classes.add;
                break;
        }
        return (
            <button onClick={onClick} className={[classes.rectangularButton, primaryClassName, className].join(" ")} type="button">
                <img src={process.env.PUBLIC_URL + fileName} alt="sirio rectangular button" height="22px" />
            </button>
        )
    }
}