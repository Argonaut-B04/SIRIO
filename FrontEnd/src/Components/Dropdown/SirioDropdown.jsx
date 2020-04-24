import React, { Component } from 'react';

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
        const { state, props, toggleOpen } = this;
        const { listOpen } = state;
        const { headerClass, activeClass, menuClass, headerTitle } = props;
        return (
            <>
                <div className={listOpen ? [headerClass, activeClass].join(' ') : headerClass} onClick={toggleOpen} data-tip={listOpen ? "Klik untuk menutup" : "Klik untuk melihat lebih"}>
                    <span>{headerTitle}</span> <span className="ml-auto"> ▼ </span>
                </div>
                {listOpen && <div className={menuClass}>
                    {this.props.children}
                </div>}
            </>
        );
    }
}

export default SirioDropdown;