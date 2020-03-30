import React, { Component } from 'react';
import classes from "./SirioDropdown.module.css";

/**
 * Komponen Dropdown untuk Sirio secara Umum
 * Gunakan SirioDropdownItem sebagai children
 * 
 * Props yang tersedia:
 * - headerClass        : cssClass, kelas untuk header
 * - activeClass        : cssClass, ketika dropdown terbuka
 * - menuClass          : cssClass, kelas untuk menu dropdown yang terbuka
 * - headerTitle        : String, nama dropdown
 */
class SirioDropdown extends Component {

    constructor(props) {
        super(props);

        this.state = {
            listOpen: false
        }

        this.toggleOpen = this.toggleOpen.bind(this);
    }

    toggleOpen() {
        this.setState(prevState => ({
            listOpen: !prevState.listOpen
        }))
    }

    render() {
        return (
            <div className={classes.dropdown}>
                <div className={this.state.listOpen ? [this.props.headerClass, this.props.activeClass].join(' ') : this.props.headerClass} onClick={() => this.toggleOpen()}>
                    <span>{this.props.headerTitle}</span> <span className="ml-auto"> ▼ </span>
                </div>
                {this.state.listOpen && <div className={this.props.menuClass}>
                    {this.props.children}
                </div>}
            </div>
        );
    }
}

export default SirioDropdown;