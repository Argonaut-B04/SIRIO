import React, { Component } from 'react';
import classes from './SirioMainLayout.module.css';
import SirioSideNav from "../Components/SideNav/SirioSideNav";

/**
 * Kelas yang akan digunakan untuk menampilkan layout utama halaman Sirio
 * 
 * Komponen included:
 * - Side Navigation
 * - Lokasi konten halaman
 */
export default class SirioMainLayout extends Component {
    render() {
        return (
            <>
                <SirioSideNav />
                <div className={classes.mainContent}>
                    <div className={classes.pageContent}>
                        {this.props.children}
                    </div>
                </div>
            </>
        );
    }
}